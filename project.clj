(defproject org.pinkgorilla/gorilla-renderable "2.1.23-SNAPSHOT"
  :description "The protocol for custom rendering in Pink Gorilla Notebook."
  :url "https://github.com/pink-gorilla/gorilla-renderable"
  :license {:name "MIT"}
  ;:deploy-repositories [["releases" :clojars]]
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; [org.clojure/clojurescript "1.10.520"]
                 [org.clojure/data.json "0.2.6"]            ; used by old vega renderer
                 [hiccup "1.0.5"]
                 [reagent "0.8.1"
                  :exclusions [org.clojure/tools.reader]]   ; needed by pinkie r/atom
                 ]                                          ; used in hiccup rendering
  
  :source-paths ["src"]
  :test-paths ["test"]
  ;; :plugins [[lein-doo "0.1.11"]]
  
  :profiles {:dev {:dependencies [[thheller/shadow-cljs "2.8.80"]
                                  [thheller/shadow-cljsjs "0.0.21"]
                                  [clj-kondo "2019.11.23"]]
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
  
  :aliases {"build-shadow-ci" ["run" "-m" "shadow.cljs.devtools.cli" "compile" ":ci"]
            "bump-version" ["change" "version" "leiningen.release/bump-version"]}

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]])


