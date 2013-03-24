class WordsController < ApplicationController
  before_filter :ensure_logged_in
  # GET /words
  # GET /words.json
  def index
    @words = context.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @words }
    end
  end

  # GET /words/1
  # GET /words/1.json
  def show
    @word = context.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @word }
    end
  end

  # GET /words/new
  # GET /words/new.json
  def new
    @word = context.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @word }
    end
  end

  # GET /words/1/edit
  def edit
    @word = context.find(params[:id])
  end

  # POST /words
  # POST /words.json
  def create
    @word = context.new(params[:word])

    if @word.save
      if @word.needs_translation?
        redirect_to set_translation_path(@word)
      else
        respond_to do |format|
          format.html { redirect_to @word, notice: 'Word was successfully created.' }
          format.json { render json: @word, status: :created, location: @word }
        end
      end

    else
      respond_to do |format|
        format.html { render action: "new" }
        format.json { render json: @word.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /words/1
  # PUT /words/1.json
  def update
    @word = context.find(params[:id])

    respond_to do |format|
      if @word.update_attributes(params[:word])
        format.html { redirect_to @word, notice: 'Word was successfully saved.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @word.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /words/1
  # DELETE /words/1.json
  def destroy
    @word = context.find(params[:id])
    @word.destroy

    respond_to do |format|
      format.html { redirect_to words_url }
      format.json { head :no_content }
    end
  end

  def set_translation
    handle_set_translation('set_translation') do |word|
      if word.polish?
        @translations = Translation.english_translations(word.in_polish)
      else
        @translations = Translation.polish_translations(word.in_english)
      end
    end
  end

  def set_english_translation
    handle_set_translation('set_english_translation') do |word|
      @translations = Translation.english_translations(word.in_polish)
    end
  end

  def set_polish_translation
    handle_set_translation('set_polish_translation') do |word|
      @translations = Translation.polish_translations(word.in_english)
    end
  end

  private
  def handle_set_translation(action_name, &block)
    @word = Word.find(params[:id])
    begin
      block.call(@word)
    rescue NoMethodError
      return redirect_to edit_word_path(@word), :notice => "No translation found, please provide your own"
    rescue OpenURI::HTTPError, SocketError, TimeoutError
      return redirect_to @word, :notice => "There was an error with translation service, try again or provide your own translation, using edit"
    end

    return render action_name
  end


  def context
    Word.where(:user_id => current_user.id)
  end

  def ensure_logged_in
    unless signed_in? #signed_in? in SessionsHelper
      store_location
      redirect_to signin_path, notice: "Please sign in"
    end
  end
end
