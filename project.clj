(defproject org.pinkgorilla/gorilla-renderable "2.1.20-SNAPSHOT"
  :description "The protocol for custom rendering in Pink Gorilla Notebook."
  :url "https://github.com/pink-gorilla/gorilla-renderable"
  :license {:name "MIT"}
  ;:deploy-repositories [["releases" :clojars]]
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [org.clojure/data.json "0.2.6"]            ; used by old vega renderer
                 [hiccup "1.0.5"]
                 [reagent "0.8.1"
                  :exclusions [org.clojure/tools.reader]]   ; needed by pinkie r/atom
                 ]                                          ; used in hiccup rendering

  :source-paths ["src"]
  :test-paths ["test"]
  :plugins [[lein-doo "0.1.11"]]

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

  :aliases {"bump-version" ["change" "version" "leiningen.release/bump-version"]}

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]]

  :doo {:build    "test"
        :default  [#_:chrome #_:phantom :karma-phantom]
        :browsers [:chrome #_:firefox]
        :alias    {:default [:chrome-headless]}
        :karma    {:config {"proxies" {"/target" "./target"}}}
        :paths
                  {;; :phantom "phantomjs --web-security=false"
                   :karma "./node_modules/karma/bin/karma --port=9881 --no-colors"}}
  :cljsbuild {:builds [{:id           "test"
                        :source-paths ["src" "test"]
                        :compiler     {:output-dir    "target/cljsbuild/js/doo"
                                       :output-to     "target/cljsbuild/js/doo-runner.js"
                                       :main          pinkgorilla.doo-runner
                                       :optimizations :none}}]})


