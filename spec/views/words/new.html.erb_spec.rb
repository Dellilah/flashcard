require 'spec_helper'

describe "words/new" do
  before(:each) do
    assign(:word, stub_model(Word,
      :in_polish => "MyText",
      :in_english => "MyText"
    ).as_new_record)
  end

  it "renders new word form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => words_path, :method => "post" do
      assert_select "textarea#word_in_polish", :name => "word[in_polish]"
      assert_select "textarea#word_in_english", :name => "word[in_english]"
    end
  end
end
