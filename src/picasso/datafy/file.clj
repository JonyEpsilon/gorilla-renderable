(ns picasso.datafy.file
  (:require [clojure.datafy :use datafy])
  (:import [java.nio.file Paths Path Files]))

; stolen from:
; https://github.com/pedro-w/nav-demo/blob/master/src/nav_demo.clj

(defn make-path
  "Create a java.nio.file.Path from an array of path components"
  [part & parts]
  (Paths/get part (into-array String parts)))

(defn maybe-children [path]
  (seq (.toArray (Files/list path))))

(defn maybe-children
  "If path is a directory, return the names of its children as a seq.
  Otherwise return nil."
  [path]
  (try
    (when (Files/isDirectory path (into-array java.nio.file.LinkOption []))
      (-> (Files/list path)
          .iterator
          iterator-seq))
    (catch java.nio.file.AccessDeniedException e [])))

(extend-protocol clojure.core.protocols/Datafiable
  java.nio.file.Path
  ;; For a Path, return its name, size and children (if any) as pure data
  (datafy [p] (conj {:name (.toString p)
                     :size (Files/size p)}
                    (if-let [children (maybe-children p)]
                      {:children children}))))

(defn -main []
  (println (datafy (make-path "/"))))