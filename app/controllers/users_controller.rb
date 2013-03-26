class UsersController < ApplicationController
  before_filter :ensure_signing_in, only: [:edit, :update]
  before_filter :ensure_correct_user, only: [:edit, :update]
  before_filter :ensure_not_signed_in, only: [:new, :create]

  def show
    @user = User.find(params[:id])
  end

  def new
    @user = User.new
  end

  def create
    @user = User.new(params[:user])
    if @user.save
      flash[:success] = "Welcome to FlashCard"
      sign_in @user
      redirect_to @user
    else
      render 'new'
    end
  end

  def edit
  end

  def update
    if @user.update_attributes(params[:user])
      flash[:success] = "Profile updated"
      sign_in @user
      redirect_to @user
    else
      render 'edit'
    end
  end

  private
  def ensure_signing_in
    unless signed_in? #signed_in? in SessionsHelper
      store_location
      redirect_to signin_path, notice: "Please sign in"
    end
  end

  def ensure_correct_user
    @user = User.find(params[:id])
    unless current_user?(@user)
      flash[:error] = "You don't have rights to do it!"
      redirect_to(root_path) unless current_user?(@user) #current_user? in SessionsHelper
    end
  end

  def ensure_not_signed_in
    if signed_in? #signed_in? in SessionsHelper 
      redirect_to current_user
    end
  end
end


