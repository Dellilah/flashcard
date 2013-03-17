require 'spec_helper'

describe "Static pages" do

  subject { page }

  it "should have the right links on the layout" do
    visit root_path
    should have_content('Welcome')

    click_link "FlashCard"
    should have_content('Welcome')

    click_link "Sign in"
    should have_selector('h1',    text: 'Sign in')
    should have_selector('title', text: full_title('Sign in'))

    click_link "Sign up"
    should have_selector('h1',    text: 'Sign up')
    should have_selector('title', text: full_title('Sign up'))
  end

end
