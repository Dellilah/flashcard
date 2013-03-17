class SessionsController < ApplicationController
  before_filter :ensure_not_signed_in, only: [:new]

  def new
  end

  def create_from_facebook
    auth = request.env["omniauth.auth"]
    user = User.find_by_facebook_uid(auth["uid"]) || User.create_with_omniauth(auth)
    if user
      sign_in user
      flash[:success] = "Successfully signed in with facebook"
      redirect_to user #redirect_back_or in SessionsHelper
    else
      redirect_to root_url, notice: "Email your facebook has provided was already used to signup"
    end
  end

  def omniauth_callback_error
    if (params[:error_reason] == "user_denied")
      flash[:error] = "You have denied the access!"
    else
      flash[:error] = "Error with authentication"
    end
    
    redirect_to root_url
  end

  def create
    user = User.find_by_email(params[:session][:email].downcase)
    if user && user.authenticate(params[:session][:password])
      sign_in user
      flash[:success] = "Successfully signed in"
      redirect_back_or user #redirect_back_or in SessionsHelper
    else
      flash.now[:error] = "Invalid email/password combination"
      render 'new'
    end
  end

  def destroy
    sign_out
    flash[:success] = "Successfully signed out"
    redirect_to root_url
  end

  private

  def ensure_not_signed_in
    if signed_in? #signed_in? in SessionsHelper 
      redirect_to current_user
    end
  end
end
