# encoding: UTF-8
require 'open-uri'
class Translation
  API_KEY = '8b863'
  BASE_URL = "http://api.wordreference.com/#{API_KEY}/json/"
  BASE_PL_EN_URL = "#{BASE_URL}plen/"
  BASE_EN_PL_URL = "#{BASE_URL}enpl/"

  def self.english_translations(word)
    url = URI.encode(BASE_PL_EN_URL + word)

    response = nil
    timeout(2) do
      response = JSON.parse(open(url).read)
    end

    extract_english_translations_from_response(response)
  end

  def self.polish_translations(word)
    url = URI.encode(BASE_EN_PL_URL + word)

    response = nil
    timeout(2) do
      response = JSON.parse(open(url).read)
    end

    extract_polish_translations_from_response(response)
  end

  def self.extract_polish_translations_from_response(response)
    translations = response["term0"]["PrincipalTranslations"].map do |_, translation|
      translation["FirstTranslation"]["term"]
    end

    remove_invalid_entries(translations)
  end

  def self.extract_english_translations_from_response(response)
    translations = response["term0"]["OtherSideEntries"].map do |_, translation|
      translation["OriginalTerm"]["term"]
    end

    remove_invalid_entries(translations)
  end

  def self.remove_invalid_entries(translations)
    translations.delete_if do |value|
      value.blank? || value == '-'
    end
  end

  private_class_method :extract_polish_translations_from_response
  private_class_method :extract_english_translations_from_response
  private_class_method :remove_invalid_entries
end
