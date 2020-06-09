(ns pinkie.clj-types
  "converts clojure values to html representation"
  (:require
   [pinkie.converter :refer [Pinkie to-pinkie]]))


;; helper functions

;; A lot of things to-pinkie to an HTML span, with a class to mark the type of thing. 
;; This helper constructs the rendered
;; value in that case.


(defn- span-render
  [thing class]
  [:span {:class class} (pr-str thing)])


;; Renderers for basic Clojure forms **

;; A default, catch-all renderer that takes anything we don't know what to do with


(extend-type Object
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-unknown")))

;; nil values are a distinct thing of their own
(extend-type nil
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-nil")))

(extend-type Boolean
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-boolean")))

(extend-type clojure.lang.Symbol
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-symbol")))

(extend-type clojure.lang.Keyword
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-keyword")))

(extend-type clojure.lang.Var
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-var")))

(extend-type clojure.lang.Atom
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-atom")))

(extend-type clojure.lang.Agent
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-agent")))

(extend-type clojure.lang.Ref
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-ref")))

(extend-type java.lang.String
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-string")))

(extend-type java.lang.Character
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-char")))

(extend-type java.lang.Long
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-long")))

(extend-type java.lang.Double
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-double")))

(extend-type clojure.lang.BigInt
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-bigint")))

(extend-type java.math.BigDecimal
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-bigdecimal")))

(extend-type clojure.lang.Ratio
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-ratio")))

(extend-type java.lang.Class
  Pinkie
  (to-pinkie [self]
    (span-render self "clj-class")))

;; renderers for collection of items

; span is used by
(defn- list-alike [{:keys [class open close sep]} self]
  [:div
   open
   (into [:div {:class class
         ;:value (pr-str self)
                }]
         (map to-pinkie self))
   close])

(extend-type clojure.lang.IPersistentVector
  Pinkie
  (to-pinkie [self]
    (list-alike {:class "clj-vector"
                 :open "["
                 :close "]"
                 :sep " "}
                self)))

(extend-type clojure.lang.LazySeq
  Pinkie
  (to-pinkie [self]
    (list-alike {:class "clj-lazy-seq"
                 :open "("
                 :close ")"
                 :sep " "}
                self)))

(extend-type clojure.lang.IPersistentList
  Pinkie
  (to-pinkie [self]
    (list-alike {:class "clj-list"
                 :open "("
                 :close ")"
                 :sep " "}
                self)))

;; TODO: is this really necessary? Is there some interface I'm missing for lists? Or would just ISeq work?
(extend-type clojure.lang.ArraySeq
  Pinkie
  (to-pinkie [self]
    (list-alike {:class "clj-list"
                 :open "("
                 :close ")"
                 :sep " "}
                self)))

(extend-type clojure.lang.Cons
  Pinkie
  (to-pinkie [self]
    (list-alike {:class "clj-list"
                 :open "("
                 :close ")"
                 :sep " "}
                self)))


;; When we to-pinkie a map we will map over its entries, which will yield key-value pairs represented as vectors. To to-pinkie
;; the map we to-pinkie each of these key-value pairs with this helper function. They are rendered as list-likes with no
;; bracketing. These will then be assembled in to a list-like for the whole map by the IPersistentMap to-pinkie function.


(defn- render-map-entry
  [entry]
  {:type :list-like
   :open nil
   :close nil
   :separator [:span " "]
   :items (map to-pinkie entry)
   :value (pr-str entry)})

(extend-type clojure.lang.IPersistentMap
  Pinkie
  (to-pinkie [self]
    (list-alike {:class  "clj-map"
                 :open "{"
                 :close "}"
                 :sep " "}
                self)))

(extend-type clojure.lang.IPersistentSet
  Pinkie
  (to-pinkie [self]
    (list-alike {:class  "clj-set"
                 :open "#{"
                 :close "}"
                 :sep " "}
                self)))

;; A record is like a map, but it is tagged with its type
(extend-type clojure.lang.IRecord
  Pinkie
  (to-pinkie [self]
    (list-alike {:class  "clj-record"
                 :open "{"
                 :close "}"
                 :sep " "
                 :type (str "#" (pr-str (type self)))}
                self)))