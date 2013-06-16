module RepetitionsHelper

  def draw_repetition
    current_user.words.joins(:repetition).where("repetitions.date <= ?", DateTime.now.to_date).shuffle.first
  end

  def draw_repetition_other_than(id)
    current_user.words.joins(:repetition).where("repetitions.date <= ? AND words.id NOT IN (?)", DateTime.now.to_date, id).shuffle.first
  end
end
