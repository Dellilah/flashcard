class Word < ActiveRecord::Base
  attr_accessible :in_english, :in_polish
  belongs_to :user

  def polish?
    in_polish.present? && in_english.blank?
  end

  def english?
    in_english.present? && in_polish.blank?
  end

  def needs_translation?
    polish? || english?
  end

  def as_json(*)
    {
      :id => id,
      :in_polish => in_polish,
      :in_english => in_english,
      :created_at => created_at,
      :updated_at => updated_at
    }
  end
end
