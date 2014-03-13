$:.unshift File.expand_path("/home/ted/ruby/thrift-client/lib", __FILE__)
$:.unshift File.expand_path("../", __FILE__)
require "test/unit"
require "thrift_client"
require "yaml"
require 'em-synchrony'
require 'em-synchrony/fiber_iterator'
require "test_service"

class TestException < Test::Unit::TestCase
  include Test::Unit::Assertions
  def setup
    super
    root = File.expand_path("../", __FILE__)
    @config = YAML.load_file(File.join(root, 'config.yaml'))
    puts "setup"
  end

  def format_backtrace(e)
    backtrace = ""
    e.backtrace.each { | s |
      backtrace.concat(s).concat("\n")
    }
    backtrace
  end

  def test_make_timeout
    client = ThriftClient.new(@config['test'])
    begin
      client.maketimeout
    rescue Exception => e
      backtrace = format_backtrace(e)
      puts "#{e.class} #{e.message} #{backtrace}"
    end
  end

  def test_put
    client = ThriftClient.new(@config['test'])
    begin
      foo = Thrift::Test::Foo.new
      foo.id = 1
      foo.count = 2
      foo.name = "name1"
      foo.deleted = false
      client.put foo
    rescue Exception => e
      backtrace = format_backtrace(e)
      puts "#{e.class} #{e.message} #{backtrace}"
    end
  end

  def test_get
    client = ThriftClient.new(@config['test'])
    begin
      puts client.get nil
    rescue Exception => e
      backtrace = format_backtrace(e)
      puts "#{e.class} #{e.message} #{backtrace}"
    end
  end

  def teardown
    super
    puts "teardown"
  end
end

