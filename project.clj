(defproject org.pinkgorilla/gorilla-renderable "2.0.13"
  :description "The protocol for custom rendering in gorilla REPL."
  :url "https://github.com/pink-gorilla/gorilla-renderable"
  :license {:name "MIT"}
  
  ;:deploy-repositories [["releases" :clojars]]
  :repositories [["clojars" {:url "https://clojars.org/repo"
                             :username "pinkgorillawb"
                             :sign-releases false}]]
  
  :dependencies 
  [[org.clojure/clojure "1.9.0-alpha14"]
   [org.clojure/data.json "0.2.6"] ; used by old vega renderer
   [hiccup "1.0.5"] ; used in hiccup rendering
   ])


