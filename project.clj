(defproject org.pinkgorilla/picasso "3.1.8"
  :description "renderer lookup engine"
  :url "https://github.com/pink-gorilla/picasso"
  :license {:name "MIT"}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]

   ;; TODO: prep tasks breaks alias???
  ;; :prep-tasks ["build-shadow-ci"]

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]]

   :target-path  "target/jar"
  
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.rpl/specter "1.1.3"]
                 [org.clojure/data.codec "0.1.1"]] ; image base64 encoding

  :profiles {:cljs {:dependencies [[thheller/shadow-cljs "2.8.80"]
                                  [thheller/shadow-cljsjs "0.0.21"]]} 
             
             :dev {:dependencies [
                                  [clj-kondo "2019.11.23"]]
                   :plugins      [[lein-cljfmt "0.6.6"]
                                  [lein-cloverage "1.1.2"]
                                  [lein-shell "0.5.0"]]
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

  :aliases {"bump-version"
            ["change" "version" "leiningen.release/bump-version"]

            "build-shadow-ci" ^{:doc "Build shadow-cljs ci"}
            ["run" "-m" "shadow.cljs.devtools.cli" "compile" ":ci"]

            "test-run" ^{:doc "Test compiled JavaScript."}
            ["shell" "./node_modules/karma/bin/karma" "start" "--single-run"]

            "test-js" ^{:doc "Compile & Run JavaScript."}
            ["with-profile" "+cljs" "do" "build-shadow-ci" ["test-run"]]})


