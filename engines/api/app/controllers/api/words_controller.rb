module Api
  class WordsController < ApplicationController
    def index
      render :json => context
    end

    def show
      word = context.where(:id => params[:id]).first
      render :json => word
    end

    def create
      if params[:in_english] && params[:in_polish]
        word = context.create(:in_english => params[:in_english], :in_polish => params[:in_polish])
        render :json => word
      else
        render :json => { :message => "You have to provide word in english and polish" }, :status => :unprocessable_entity
      end
    end

    def update
      word = context.where(:id => params[:id]).first
      if word && params[:in_english] && params[:in_polish]
        word.update_attributes(:in_english => params[:in_english], :in_polish => params[:in_polish])
        render :json => word
      else
        render :json => { :message => "You have to provide valid data" }, :status => :unprocessable_entity
      end
    end

    def destroy
      word = context.where(:id => params[:id]).first
      if word
        word.destroy
        render :json => { :message => "Succesfully deleted word" }
      else
        render :json => { :message => "Unable to find such word" }, :status => :unprocessable_entity
      end
    end

    def get_english_translations
      if params[:in_polish]
        handle_get_translations do
          Translation.english_translations(params[:in_polish])
        end
      else
        render :json => { :message => "You have to provide valid data" }, :status => :unprocessable_entity
      end
    end

    def get_polish_translations
      if params[:in_english]
        handle_get_translations do
          Translation.polish_translations(params[:in_english])
        end
      else
        render :json => { :message => "You have to provide valid data" }, :status => :unprocessable_entity
      end
    end

    private
    def context
      current_user.words
    end

    def handle_get_translations(&block)
      begin
        translations = block.call
        render :json => translations.to_json
      rescue NoMethodError
       render :json => { :message => "No translations found" }, :status => 404
      rescue OpenURI::HTTPError, SocketError, TimeoutError
       render :json => { :message => "There was an error with translation service, try again" }, :status => 503
      end
    end
  end
end
