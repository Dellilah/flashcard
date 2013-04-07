module Api
  class ApplicationController < ActionController::Base
    before_filter :authorize

    private
    def authorize
      remember_token = params[:api_token]
      if remember_token && User.where(:remember_token => remember_token).first
        @current_user = User.where(:remember_token => remember_token).first
      else
        render :json => { :error => 'Unauthorized' }, :status => 401
        return false
      end
    end

    def current_user
      @current_user
    end
  end
end
