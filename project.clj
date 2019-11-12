(defproject org.pinkgorilla/gorilla-renderable "2.1.0"
  :description "The protocol for custom rendering in Pink Gorilla Notebook."
  :url "https://github.com/pink-gorilla/gorilla-renderable"
  :license {:name "MIT"}
  ;:deploy-repositories [["releases" :clojars]]
  :repositories [["clojars" {:url "https://clojars.org/repo"
                             :username "pinkgorillawb"
                             :sign-releases false}]]
  :dependencies
  [[org.clojure/clojure "1.10.1"]
   [org.clojure/clojurescript "1.10.520"]
   [org.clojure/data.json "0.2.6"] ; used by old vega renderer
   [hiccup "1.0.5"]] ; used in hiccup rendering
  
  :source-paths ["src"]
  :test-paths ["test"]
  :plugins [[lein-doo "0.1.11"]]
  :doo {:build "test"
        :default  [#_:chrome #_:phantom :karma-phantom]
        :browsers [:chrome #_:firefox]
        :alias {:default [:chrome-headless]}
        :paths
        {;; :phantom "phantomjs --web-security=false"
         :karma "./node_modules/karma/bin/karma --port=9881 --no-colors"}}
  :cljsbuild
  {:builds [{:id "test"
             :source-paths ["src" "test"]
             :compiler {:output-to "resources/public/js/testable.js"
                        :main pinkgorilla.doo-runner
                        :optimizations :none}}]})


