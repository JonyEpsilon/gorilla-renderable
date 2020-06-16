(ns picasso.render-image-test
  (:require
   [clojure.test :refer :all]
   [picasso.protocols :refer [render]]
   [picasso.render.image :refer [image-view]])
  (:import java.io.File
           java.io.InputStream
           javax.imageio.ImageIO))

(deftest render-image
  (let [img (ImageIO/read (File. "test/picasso/brain.jpeg"))
        view (image-view img)
        rendered (render view)]
    (is (= {:alt ""
            :type "png"
            :width 236
            :height 177}
           (dissoc view :image)))
    (is (= {:type :html}
           (dissoc rendered :content :value)))))