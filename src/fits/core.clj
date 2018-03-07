(ns fits.core
  (:require [clojure.java.io :refer :all])
  (:gen-class))
(def file-name "resources/180305_0000001884.fits")

(let [f (file file-name )]
  (with-open [in (input-stream f)]
   (let [size (.length f)
         buf (byte-array size)
         _ (.read in buf)]
     (println "Read" size "bytes.")
    (with-open [out (output-stream (file "test.fits"))]
      (.write out (subvec buf 0 1123))
      (.write out "_OBS")
      (.write out (subvec buf 1127 size)) ))))


(defn edit-all-files-in-directory [dir]
  (let [d (clojure.java.io/file dir)
        fs (file-seq d)
        fs (rest fs)]
    (doseq [f fs]
      (let [orig   (slurp f)
            edited (.replace orig "DATE    " "DATE_OBS")
            _ (prn "editing: " (.getName f))]
        (spit (str "edited-" (.getName f)) edited )))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (edit-all-files-in-directory (first args)))
