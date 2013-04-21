class HomeController < ApplicationController
  def index
    if signed_in?
      redirect_to :controller => 'memo', :action => 'home'
    end
  end
end
