class User < ApplicationRecord
  has_secure_token :active_token
  validates :name, presence: true
  validates :platform_name, presence: true, inclusion: %w(Twitter Facebook Google)

end
