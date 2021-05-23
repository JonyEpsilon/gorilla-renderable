(defproject org.pinkgorilla/picasso "3.1.24-SNAPSHOT"
  :description "renderer lookup engine"
  :url "https://github.com/pink-gorilla/picasso"
  :license {:name "MIT"}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]]

  :source-paths ["src"]
  :target-path  "target/jar"

  :dependencies [[org.clojure/tools.reader "1.3.5"] ; tag version
                 [org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.3.618"]
                 [com.taoensso/timbre "5.1.2"] ; clj/cljs logging
                 [com.rpl/specter "1.1.3"] ; hiccup -> reagent
                 [com.lucasbradstreet/cljs-uuid-utils "1.0.2"] ; uuid - clojurescript
                 [org.clojure/data.codec "0.1.1"]] ; image base64 encoding

  :profiles {:demo {:source-paths ["profiles/demo/src"]}         
             :webly {:dependencies [[org.pinkgorilla/webly "0.2.40"]
                                    [org.pinkgorilla/pinkie "0.3.3"] 
                                    [org.pinkgorilla/ui-markdown "0.0.6"]
                                    [org.pinkgorilla/ui-code "0.0.5"]
                                    ]
                     :source-paths ["profiles/webly/src"]
                     :resource-paths  ["target/webly"
                                       "profiles/webly/resources"]}

             :dev {:dependencies [[clj-kondo "2021.04.23"]]
                   :plugins      [[lein-cljfmt "0.6.6"]
                                  [lein-cloverage "1.1.2"]
                                  [lein-shell "0.5.0"]
                                  [lein-ancient "0.6.15"]
                                  ]
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

            "webly"
            ["with-profile" "+webly" "run" "-m" "demo.app"]

            "test-js" ^{:doc "run unit test JavaScript."}
            ["do"
             ["webly" "ci"]
             ["shell" "npm" "test"]
             ]
            
             "demo"
             ["with-profile" "+webly" "run" "-m" "demo.app" "watch"]
})


