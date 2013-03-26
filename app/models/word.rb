class Word < ActiveRecord::Base
  attr_accessible :in_english, :in_polish

  def polish?
    in_polish.present? && in_english.blank?
  end

  def english?
    in_english.present? && in_polish.blank?
  end

  def needs_translation?
    polish? || english?
  end
end
