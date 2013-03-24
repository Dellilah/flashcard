class Word < ActiveRecord::Base
  attr_accessible :in_english, :in_polish

  def polish_word?
    in_polish.present? && in_english.blank?
  end

  def english_word?
    in_english.present? && in_polish.blank?
  end

  def needs_translation?
    polish_word? || english_word?
  end
end
