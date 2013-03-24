class CreateWords < ActiveRecord::Migration
  def change
    create_table :words do |t|
      t.text :in_polish
      t.text :in_english
      t.belongs_to :user

      t.timestamps
    end
  end
end
