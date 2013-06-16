class CreateRepetitions < ActiveRecord::Migration
  def change
    create_table :repetitions do |t|
      t.belongs_to :word
      t.integer :repetitions
      t.date :date

      t.timestamps

    end

  end
end
