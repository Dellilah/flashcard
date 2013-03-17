include ApplicationHelper

def sign_in(user, password = nil)
  visit signin_path
  fill_in "Email", with: user.email
  fill_in "Password", with: (password || user.password)
  click_button "Sign in"
  #For no-capybara
  cookies[:remember_token] = user.remember_token
end

def fill_user_info_form(name, email, password, password_confirmation=nil)
  fill_in "Name", with: name
  fill_in "Email", with: email
  fill_in "Password", with: password
  if(password_confirmation)
    fill_in "Confirmation", with: password_confirmation
  else
    fill_in "Confirmation", with: password
  end
end

def configure_valid_mock_fb_auth(user)
  OmniAuth.config.add_mock(:facebook, :uid => '12345', info: { name: user.name, email: user.email })
end

def configure_invalid_mock_fb_auth
  OmniAuth.config.mock_auth[:facebook] = :invalid_credentials
end
