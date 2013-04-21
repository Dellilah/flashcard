class MemoController < ApplicationController
  before_filter :ensure_logged_in
  
  def home
    @words = context.all.shuffle.take(3)
  end

  private

  def context
    current_user.words
  end

  def ensure_logged_in
    unless signed_in? #signed_in? in SessionsHelper
      store_location
      redirect_to signin_path, notice: "Please sign in"
    end
  end
end
