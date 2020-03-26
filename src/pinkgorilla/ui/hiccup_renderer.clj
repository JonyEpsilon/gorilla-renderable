(ns pinkgorilla.ui.hiccup_renderer
  "converts clojure values to html representation"
  (:require
   [clojure.string :as string]
   [pinkgorilla.ui.gorilla-renderable :refer [Renderable render]]))


;;; Helper functions

;; Make a string safe to display as HTML


(defn- escape-html
  [str]
  ;; this list of HTML replacements taken from underscore.js
  ;; https://github.com/jashkenas/underscore
  (string/escape str {\& "&amp;", \< "&lt;", \> "&gt;", \" "&quot;", \' "&#x27;"}))

;; A lot of things render to an HTML span, with a class to mark the type of thing. This helper constructs the rendered
;; value in that case.
(defn- span-render
  [thing class]
  {:type :html
   :content [:span {:class class} (pr-str thing)]
   ;; (str "<span class='" class "'>" (escape-html (pr-str thing)) "</span>")
   :value (pr-str thing)})

(defn- span
  [class value]
  [:span {:class class} value]
  ;; "<span class='clj-lazy-seq'>)</span>"
  )

;;; ** Renderers for basic Clojure forms **

;; A default, catch-all renderer that takes anything we don't know what to do with and calls str on it.
(extend-type Object
  Renderable
  (render [self]
    (span-render self "clj-unkown")))

;; nil values are a distinct thing of their own
(extend-type nil
  Renderable
  (render [self]
    (span-render self "clj-nil")))

(extend-type Boolean
  Renderable
  (render [self]
    (span-render self "clj-boolean")))

(extend-type clojure.lang.Symbol
  Renderable
  (render [self]
    (span-render self "clj-symbol")))

(extend-type clojure.lang.Keyword
  Renderable
  (render [self]
    (span-render self "clj-keyword")))

(extend-type clojure.lang.Var
  Renderable
  (render [self]
    (span-render self "clj-var")))

(extend-type clojure.lang.Atom
  Renderable
  (render [self]
    (span-render self "clj-atom")))

(extend-type clojure.lang.Agent
  Renderable
  (render [self]
    (span-render self "clj-agent")))

(extend-type clojure.lang.Ref
  Renderable
  (render [self]
    (span-render self "clj-ref")))

(extend-type java.lang.String
  Renderable
  (render [self]
    (span-render self "clj-string")))

(extend-type java.lang.Character
  Renderable
  (render [self]
    (span-render self "clj-char")))

(extend-type java.lang.Long
  Renderable
  (render [self]
    (span-render self "clj-long")))

(extend-type java.lang.Double
  Renderable
  (render [self]
    (span-render self "clj-double")))

(extend-type clojure.lang.BigInt
  Renderable
  (render [self]
    (span-render self "clj-bigint")))

(extend-type java.math.BigDecimal
  Renderable
  (render [self]
    (span-render self "clj-bigdecimal")))

(extend-type clojure.lang.Ratio
  Renderable
  (render [self]
    (span-render self "clj-ratio")))

(extend-type java.lang.Class
  Renderable
  (render [self]
    (span-render self "clj-class")))

(extend-type clojure.lang.IPersistentVector
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-vector" "[")
     :close (span "clj-vector" "]")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

(extend-type clojure.lang.LazySeq
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-lazy-seq" "(")
     :close (span "clj-lazy-seq" ")")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

(extend-type clojure.lang.IPersistentList
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-list" "(")
     :close (span "clj-list" ")")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

;; TODO: is this really necessary? Is there some interface I'm missing for lists? Or would just ISeq work?
(extend-type clojure.lang.ArraySeq
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-list" "(")
     :close (span "clj-list" ")")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

(extend-type clojure.lang.Cons
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-list" "(")
     :close (span "clj-list" ")")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))


;; When we render a map we will map over its entries, which will yield key-value pairs represented as vectors. To render
;; the map we render each of these key-value pairs with this helper function. They are rendered as list-likes with no
;; bracketing. These will then be assembled in to a list-like for the whole map by the IPersistentMap render function.


(defn- render-map-entry
  [entry]
  {:type :list-like
   :open nil
   :close nil
   :separator [:span " "]
   :items (map render entry)
   :value (pr-str entry)})

(extend-type clojure.lang.IPersistentMap
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-map" "{")
     :close (span "clj-map" "}")
     :separator [:span ", "]
     :items (map render-map-entry self)
     :value (pr-str self)}))

(extend-type clojure.lang.IPersistentSet
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-set" "#{")
     :close (span  "clj-set" "}")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

;; A record is like a map, but it is tagged with its type
(extend-type clojure.lang.IRecord
  Renderable
  (render [self]
    {:type :list-like
     :open [:span {:class "clj-record"} (str "#" (pr-str (type self)) "{")]
     :close (span "clj-record" "}")
     :separator [:span " "]
     :items (map render-map-entry self)
     :value (pr-str self)}))