;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns pinkgorilla.ui.html
  (:require 
   [pinkgorilla.ui.gorilla-renderable :as render]))

(defrecord HtmlView [content])

(defn html-view [content]
  (HtmlView. content))

(extend-type HtmlView
  render/Renderable
  (render [self]
    {:type :html :content (:content self) :value (pr-str self)}))
