require 'spec_helper'

describe ApplicationHelper do

  describe "full titile" do
    it "should include the page title" do
      full_title("foo").should =~ /foo/
    end

    it "should include the base title" do
      full_title("foo").should =~ /^FlashCard/
    end

    it "should not include a bar for the blank title" do
      full_title("").should_not =~ /\|/
    end
  end
  
end
