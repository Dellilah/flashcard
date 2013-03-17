require 'spec_helper'

describe "Authentication" do
  
  subject { page }

  describe "With omniauth" do
    before { visit root_path }

    describe "sign up with valid credentials" do
      let(:user) { FactoryGirl.build(:user) }
      before do
          configure_valid_mock_fb_auth(user)
      end

      it "should create a user" do
        expect { click_link "Sign in with FB" }.to change(User, :count)
      end

      describe "and redirect back to user page" do
        before { click_link "Sign in with FB" }

        it { should have_content "Successfully signed in with facebook" }
        it { should have_selector "h1", text: user.name }

        describe "followed by setting up a password user should be able to login with login/password" do
          before do
            user = User.last
            visit edit_user_path(user)
            fill_user_info_form(user.name, user.email, 'example')
            click_button "Save changes"
            click_link "Sign out"
            sign_in(user, 'example')
          end

          it { should have_selector('div.alert.alert-success', text: 'Successfully signed in') }
        end

        describe "not followed by setting up a password" do
          before do
            click_link "Sign out"
          end

          describe "user should not be able to login with login/password" do
            before { sign_in user }

            it { should have_selector('title', text: "Sign in") }
            it { should have_selector('div.alert.alert-error', text: 'Invalid') }
          end
        end
        describe "followed by sign out and sign in again" do
          before do
            click_link "Sign out"
          end

          it "should not create a user" do
            expect { click_link "Sign in with FB" }.not_to change(User, :count)
          end

          describe "and redirect back to user page" do
            before { click_link "Sign in with FB" }

            it { should have_content "Successfully signed in with facebook" }
            it { should have_selector "h1", text: user.name }
          end
        end
      end
    end

    describe "sign with used already email to sign up" do
      let(:user) { FactoryGirl.create(:user) }
      before do
        configure_valid_mock_fb_auth(user)
      end

      it "should not create a user" do
        expect { click_link "Sign in with FB" }.not_to change(User, :count)
      end

      describe "and redirect back to root page" do
        before { click_link "Sign in with FB" }
      
        it { should have_content "Email your facebook has provided was already used to signup" }
        it { should_not have_selector "h1", text: user.name }
      end
    end

    describe "sign up with invalid credentials" do
      let(:user) { FactoryGirl.build(:user) }
      before do
          configure_invalid_mock_fb_auth
      end

      it "should not create a user" do
        expect { click_link "Sign in with FB" }.not_to change(User, :count)
      end

      describe "and redirect back to root page" do
        before { click_link "Sign in with FB" }
      
        it { should have_content "Error" }
        it { should_not have_selector "h1", text: user.name }
      end
    end
  end



end
