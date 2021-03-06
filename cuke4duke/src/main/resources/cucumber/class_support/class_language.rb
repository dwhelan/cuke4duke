require 'cucumber/jvm_support/backtrace_filter'
require 'forwardable'

module Cucumber
  module ClassSupport
    class ClassLanguage
      class << self
        attr_reader :snippet_generator, :analyzers

        def add_analyzers(analyzer)
          @analyzers ||= []
          @analyzers << analyzer
          @snippet_generator = analyzer.snippet_generator
        end
      end
      
      extend Forwardable
      include ::Cucumber::LanguageSupport::LanguageMethods

      def_delegators :@delegate, :load_code_file, :step_matches, :begin_scenario, :end_scenario

      def initialize(step_mother)
        @delegate = ::Java::Cuke4dukeInternalJvmclass::ClassLanguage.new(self, self.class.analyzers)
      end

      def alias_adverbs(adverbs)
      end

      def snippet_text(step_keyword, step_name, multiline_arg_class = nil)
        self.class.snippet_generator.snippet_text(step_keyword, step_name, multiline_arg_class)
      end
    end
  end
end

require 'cucumber/java_support/java_analyzer'
require 'cucumber/scala_support/scala_analyzer'