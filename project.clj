(defproject org.pinkgorilla/gorilla-renderable "3.0.0"
  :description "The protocol for custom rendering in Pink Gorilla Notebook."
  :url "https://github.com/pink-gorilla/gorilla-renderable"
  :license {:name "MIT"}
  ;:deploy-repositories [["releases" :clojars]]
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; TODO: Still needed? Used by old vega renderer
                 [org.clojure/data.json "0.2.6"]
                 [hiccup "1.0.5"]]
  
  :profiles {:dev {:dependencies [[clj-kondo "2019.11.23"]]
                   :plugins      [[lein-cljfmt "0.6.6"]
                                  [lein-cloverage "1.1.2"]]
                   :aliases      {"clj-kondo" ["run" "-m" "clj-kondo.main"]}
                   :cloverage    {:codecov? true
                                  ;; In case we want to exclude stuff
                                  ;; :ns-exclude-regex [#".*util.instrument"]
                                  ;; :test-ns-regex [#"^((?!debug-integration-test).)*$$"]
                                  }
                   ;; TODO : Make cljfmt really nice : https://devhub.io/repos/bbatsov-cljfmt
                   :cljfmt       {:indents {as->                [[:inner 0]]
                                            with-debug-bindings [[:inner 0]]
                                            merge-meta          [[:inner 0]]
                                            try-if-let          [[:block 1]]}}}}

  ;; TODO: prep tasks breaks alias???
  ;; :prep-tasks ["build-shadow-ci"]
  
  :aliases {"bump-version" ["change" "version" "leiningen.release/bump-version"]}

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]])


