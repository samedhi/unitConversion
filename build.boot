(set-env!
 :source-paths #{"src/clj"}
 :dependencies '[[org.clojure/clojure "1.7.0-RC1"]])

(require 
 '[unit-conversion.boot-build :refer :all])

(task-options!
 pom {:project 'unit-conversion
      :version "0.1.0"})
