class AddRepetitionToWord < ActiveRecord::Migration
  def change
    add_column :words, :repetition_id, :integer
  end
end
