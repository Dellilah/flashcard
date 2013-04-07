module Api
  class SessionsController < ApplicationController
    skip_before_filter :authorize, :only => [:login]

    def login
      if params[:email] && params[:password]
        email = params[:email].downcase
        password = params[:password]

        user = User.find_by_email(email.downcase)
        if user && user.authenticate(password)
          render :json => { :api_token => user.remember_token }
        else
          render :json => { :message => "Could not authenticate the user" }, :status => 401
        end
      else
        render :json => { :message => "You have to provide email and password" }, :status => 401
      end
    end
  end
end
