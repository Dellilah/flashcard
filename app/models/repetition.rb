class Repetition < ActiveRecord::Base
  attr_accessible :date, :repetitions, :word
  belongs_to :word
  before_destroy :set_associate_nil
  def default_values
    self.repetitions  ||= -1
    self.date ||= DateTime.now.to_date
  end

  def reset
    self.repetitions = 0
    self.date = DateTime.now.to_date
    self.save
  end

  @@repetition_interval_days = [0, 1, 7, 30, 150, 300 ]

  def set_next_repetition_good
    self.default_values
    self.repetitions = self.repetitions + 1
    self.repetitions = [self.repetitions, @@repetition_interval_days.size-1].min
    self.date = self.date + @@repetition_interval_days[self.repetitions].days
    self.save
  end

  def set_next_repetition_wrong
    self.default_values
    self.repetitions = self.repetitions - 1
    self.repetitions = [self.repetitions, 0].max
    self.date = self.date + @@repetition_interval_days[self.repetitions].days
    self.save
  end

  def set_long_interval
     self.repetitions = @@repetition_interval_days.size - 1
     self.set_next_repetition_good
  end

  def set_associate_nil
    self.word.repetition=nil
    self.word.save
  end


end
