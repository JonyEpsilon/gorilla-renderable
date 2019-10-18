;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns pinkgorilla.ui.vega
  (:require [pinkgorilla.ui.gorilla-renderable :as render]))

(defrecord VegaView [content])

(defn vega-view [content] (VegaView. content))

(extend-type VegaView
  render/Renderable
  (render [self]
    {:type :vega :content (:content self) :value (pr-str self)}))