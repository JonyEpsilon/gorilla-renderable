;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

;; 2019-10-19 awb99: this code does not do anything. I commented it out so we know it
;; was there TODO @deas remove when it is safe to do so.

; (ns pinkgorilla.ui.vega
;  (:require [pinkgorilla.ui.gorilla-renderable :as render]))

; (defrecord VegaView [content])

;(defn vega-view [content] (VegaView. content))

;(extend-type VegaView
;  render/Renderable
;  (render [self]
;    {:type :vega :content (:content self) :value (pr-str self)}))