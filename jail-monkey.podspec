#
#  Be sure to run `pod spec lint react-native-aws-cognito-js.podspec' to ensure this is a
#  valid spec and to remove all comments including this before submitting the spec.
#
#  To learn more about Podspec attributes see http://docs.cocoapods.org/specification.html
#  To see working Podspecs in the CocoaPods repo see https://github.com/CocoaPods/Specs/
#
require "json"
package = JSON.parse(File.read(File.join(__dir__, '/package.json')))

Pod::Spec.new do |s|

  # ―――  Spec Metadata  ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――― #
  #
  #  These will help people to find your library, and whilst it
  #  can feel like a chore to fill in it's definitely to your advantage. The
  #  summary should be tweet-length, and the description more in depth.
  #

  s.name         = package['name']
  s.version      = package["version"]
  s.summary      = package['description']
  
  s.platform      = :ios, '7.0'
  # s.requires_arc  = true

  s.homepage     = "https://github.com/GantMan"
  s.license      = "MIT"


  s.author       = "Gant Laborde"
  s.source       = { :git => "https://github.com/GantMan/jail-monkey.git" }

  s.source_files  = "JailMonkey/*.{h,m}"
  
  s.dependency "React"
end
