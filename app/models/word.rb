class Word < ActiveRecord::Base
  attr_accessible :in_english, :in_polish, :image, :remote_image_url
  belongs_to :user
  belongs_to :repetition, :dependent => :delete

  mount_uploader :image, ImageUploader

  def polish?
    in_polish.present? && in_english.blank?
  end

  def english?
    in_english.present? && in_polish.blank?
  end

  def needs_translation?
    polish? || english?
  end

  def images_from_google
    begin
      timeout(4) do
        @images ||= Google::Search::Image.new(:query => in_english).take(6).map(&:thumbnail_uri)
      end
    rescue OpenURI::HTTPError, SocketError, TimeoutError
      []
    end
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

  def add_repetition
    repetition = Repetition.new(:word => self)
    repetition.set_next_repetition_good
    self.repetition = repetition
    self.save
    repetition.save
  end


end
