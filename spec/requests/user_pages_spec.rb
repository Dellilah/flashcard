require 'spec_helper'

describe "UserPages" do

  subject { page }

  describe "sign up" do 
    before { visit signup_path }

    describe "as a logged in user" do
      let(:user) { FactoryGirl.create(:user) }
      before do
        visit signin_path
        sign_in(user)
      end


      describe "page" do 
        before { visit signup_path }
        it { should_not have_selector('h1', text: 'Sign up') }
        it { should have_selector('title',  text: user.name) }
      end

      describe "submiting to the create action" do
        before  { post users_path }
        specify { response.should redirect_to(user) }
      end
      
    end

    describe "page" do
      it { should have_selector('h1',    text: 'Sign up') }
      it { should have_selector('title', text: full_title('Sign up')) }
    end

    let(:submit) { "Create my account" }

    describe "with invalid information" do
      it "should not create a user" do
        expect { click_button submit }.not_to change(User, :count)
      end

      describe "after submission" do
        before { click_button submit }

        it { should have_selector('h1',    text: 'Sign up') }
        it { should have_selector('title', text: full_title('Sign up')) }
        it { should have_content('error')}
        it { should have_content('be blank')}
        it { should have_content('invalid')}
        it { should have_content('too short')}
      end
    end

    describe "with valid information" do
      before { fill_user_info_form("Piotr Planeta", "example@com.pl", "example") }

      it "should create a user" do
        expect { click_button submit }.to change(User, :count).by(1)
      end

      describe "after saving the user" do
        before { click_button submit }
        let(:user) { User.find_by_email('example@com.pl') }

        it { should have_selector('h1',    text: user.name) }
        it { should have_selector('div.alert.alert-success', text: 'Welcome') }
        it { should have_link('Sign out') }
      end
    end
  end

  describe "profile page" do
    let(:user) { FactoryGirl.create(:user) }
    before { visit user_path(user) }

    it { should have_selector('h1',    text: user.name) }
    it { should have_selector('title', text: user.name) }
  end

  describe "edit" do
    let(:user) { FactoryGirl.create(:user) }
    before do
     sign_in(user)
     visit edit_user_path(user)
    end

    describe "page" do
      it { should have_selector('h1',    text: "Update your profile") }
      it { should have_selector('title', text: full_title('Edit user')) }
    end

    describe "with invalid information" do
      before { click_button "Save changes" }

      it { should have_content('error') }
    end

    describe "with valid information" do
      let(:new_name)  { "New Name" }
      let(:new_email) { "new@example.com" }
      before do
        fill_user_info_form(new_name, new_email, user.password)
        click_button "Save changes"
      end

      it { should have_selector('title', text: new_name) }
      it { should have_selector('div.alert.alert-success') }
      it { should have_link('Sign out', href: signout_path) }
      specify { user.reload.name.should  == new_name }
      specify { user.reload.email.should == new_email }
    end

  end

end