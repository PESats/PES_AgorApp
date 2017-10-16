class CreateUsers < ActiveRecord::Migration[5.1]
  def change
    create_table :users do |t|
      t.string :name
      t.string :image_url
      t.string :platform_name
      t.string :active_token

      t.timestamps
    end
  end
end
