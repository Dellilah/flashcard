# == Schema Information
#
# Table name: users
#
#  id              :integer          not null, primary key
#  name            :string(255)
#  email           :string(255)
#  created_at      :datetime         not null
#  updated_at      :datetime         not null
#  password_digest :string(255)
#  remember_token  :string(255)
#  facebook_uid    :string(255)
#

class User < ActiveRecord::Base
  attr_accessible :email, :name, :password, :password_confirmation

  validates :name, presence: true, length: { maximum: 50 }
  VALID_EMAIL_REGEX = /\A[\w+\-.]+@[a-z\d\-.]+\.[a-z]+\z/i
  validates :email, presence: true, format: { with: VALID_EMAIL_REGEX }, uniqueness: { case_sensitive: false }
  validates :password, length: { minimum: 5 }
  validates :password_confirmation, presence: true
  has_secure_password

  before_save { self.email.downcase! }
  before_save :create_remember_token

  def self.create_with_omniauth(auth)
    if(User.find_by_email(auth[:info][:email]).blank?)
      user = User.new
      user.facebook_uid = auth[:uid]
      user.name = auth[:info][:name]
      user.email = auth[:info][:email]
      user.save(:validate => false)
      user
    else
      false
    end
  end

  def authenticate(unencrypted_password)
    if password_digest
      BCrypt::Password.new(password_digest) == unencrypted_password && self
    else
      false
    end
  end

  private
  def create_remember_token
    self.remember_token = SecureRandom.urlsafe_base64
  end
end
