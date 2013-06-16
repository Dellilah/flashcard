class RepetitionsController < ApplicationController
  before_filter :ensure_logged_in
  include RepetitionsHelper

  def update
    @word = current_word
  end

  def show
    @word = current_word
    if @word.repetition.nil?
      flash[:notice] = 'Add this word for repetitions.'
      redirect_to words_path
    end
  end

  def asses_answer
    answer = params[:answer]
    word = current_word

    case answer
      when 'yes'
        word.repetition.set_next_repetition_good
        flash[:notice] = 'You guessed correctly'
      when 'no'
        word.repetition.set_next_repetition_wrong
        flash[:notice] = 'You guessed wrong'
      when 'know'
        word.repetition.set_long_interval
        flash[:notice] = 'You knew this word'
    end
    flash[:notice] = 'You guessed wrong'
    new_word=draw_repetition
    if new_word.nil?
      flash[:notice] = 'You repeated all your words. Congratulations!'
      redirect_to words_path
    else
      redirect_to word_repetition_path(new_word)
    end
  end

  def give_next
    word=draw_repetition
    if word.nil?
      flash[:notice] = 'There aren\'t any new repeats'
      redirect_to words_path
    else
      redirect_to word_repetition_path(word)
    end
  end

  def save
    params[:words_ids] ||= []
    @words = Word.find(params[:words_ids])
    context.all.each do|w|
      if w.repetition.nil? && @words.include?(w)
        w.add_repetition
      elsif !w.repetition.nil? && !@words.include?(w)
        w.repetition.destroy
      end
    end
    redirect_to words_path, notice: "Your changes has been successfully saved."
  end

  private

  def context
    current_user.words
  end

  def current_word
    context.find(params[:word_id])
  end

  def ensure_logged_in
    unless signed_in? #signed_in? in SessionsHelper
      store_location
      redirect_to signin_path, notice: "Please sign in"
    end
  end


end
