shared_examples_for "user page with logged in user" do
  it { should have_selector('title', text: user.name) }
  it { should have_selector('h1', text: user.name) }
  it { should have_link('Profile', href: user_path(user)) }
  it { should have_link('Settings', href: edit_user_path(user)) }
  it { should have_link('Sign out', href: signout_path) }
  it { should_not have_link('Sign up', href: signup_path) }
  it { should_not have_link('Sign in', href: signin_path) }
end