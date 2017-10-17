class AddUserIndex < ActiveRecord::Migration[5.1]
  def change
    add_index :users, :id
  end
end
