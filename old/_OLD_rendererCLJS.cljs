
(ns pinkgorilla.ui.rendererCLJS
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
  (let [v (if (nil? thing) "" (pr-str thing))]
    {:type :html
     :content (str "<span class='" class "'>" (escape-html (pr-str thing)) "</span>")
     :value v}))


;;; ** Renderers for basic Clojure forms **



; nil values are a distinct thing of their own
(extend-type nil
  Renderable
  (render [self]
    (span-render self "cljs-nil")))

(extend-type cljs.core/Keyword
  Renderable
  (render [self]
    (span-render self "cljs-keyword")))

(extend-type cljs.core/Symbol
  Renderable
  (render [self]
    (span-render self "cljs-symbol")))


(extend-type string
  Renderable
  (render [self]
    (span-render self "cljs-string")))

#_(extend-type char
    Renderable
    (render [self]
      (span-render self "cljs-char")))

(extend-type number
  Renderable
  (render [self]
    (span-render self "cljs-number")))

(extend-type boolean
  Renderable
  (render [self]
    (span-render self "cljs-boolean")))



;; When we render a map we will map over its entries, which will yield key-value pairs represented as vectors. To render
;; the map we render each of these key-value pairs with this helper function. They are rendered as list-likes with no
;; bracketing. These will then be assembled in to a list-like for the whole map by the IPersistentMap render function.
(defn- render-map-entry
  [entry]
  {:type :list-like
   :open ""
   :close ""
   :separator " "
   :items (map render entry)
   :value (pr-str entry)})

(extend-type cljs.core/PersistentArrayMap
  Renderable
  (render [self]
    {:type :list-like
     :open "<span class='cljs-map'>{</span>"
     :close "<span class='cljs-map'>}</span>"
     :separator ", "
     :items (map render-map-entry self)
     :value (pr-str self)}))

;; A default, catch-all renderer that takes anything we don't know what to do with and calls str on it.

;; https://grokbase.com/t/gg/clojure/121d2w4vhn/is-this-a-bug-extending-protocol-on-js-object
;; david nolan:
;; You should never extend js/Object.
;; It's unfortunate since this means we can't currently use js/Object to
;; provide default protocol implementations as we do in Clojure w/o fear of
;; conflicts with JavaScript libraries.

#_(extend-type js/Object
    Renderable
    (render [self]
      (span-render self "clj-unkown")))




(extend-type cljs.core/LazySeq
  Renderable
  (render [self]
    {:type :list-like
     :open "<span class='cljs-lazy-seq'>(</span>"
     :close "<span class='cljs-lazy-seq'>)</span>"
     :separator " "
     :items (map render self)
     :value (pr-str self)}))


(extend-type cljs.core/PersistentVector
  Renderable
  (render [self]
    {:type :list-like
     :open "<span class='cljs-vector'>[</span>"
     :close "<span class='cljs-vector'>]</span>"
     :separator " "
     :items (map render self)
     :value (pr-str self)}))


(extend-type default
  Renderable
  (render [self]
    (println "unkown type: " (type self))
    (span-render self "cljs-unknown")))