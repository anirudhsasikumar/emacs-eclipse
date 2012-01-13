;;; eclipse-goto-offset
;;;
;;; Copyright (C) 2010 Rick Watson.
;;;
;;; This library is free software; you can redistribute it and/or
;;; modify it under the terms of the GNU Lesser General Public License
;;; as published by the Free Software Foundation; either version 2.1 of
;;; the License, or (at your option) any later version.
;;;
;;; This library is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
;;; Lesser General Public License for more details.
;;;
;;; You should have received a copy of the GNU Lesser General Public
;;; License along with this library; if not, write to the Free Software
;;; Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
;;; USA
;;;
;;; This is an elisp function that works with the emacs plugin for
;;; eclipse.
;;;
;;; Function so that the eclipse emacs plugin can position the buffer
;;; using eclipse:curSelection.getOffset(); The problem is that the
;;; file getOffset is using may have DOS or Linux EOL's. An emacs
;;; buffer will have Linux EOL's.
;;;
;;; TODO: testing buffer-file-coding-system != undecided-unix is not
;;; really the correct approach. Figure out how to better determine
;;; what the EOL coding is for the file. Also we may need to consider
;;; BOM markers at the start of the file, depending on how eclipse
;;; handles those.
;;; Maybe a better approach is to get eclipse to give us a line offset
;;; and a character offset within the line.

(defun eclipse-goto-offset (offset)
  "Position to offset from the beginning of the buffer. Take EOL's
into account depending on the buffer's buffer-file-coding-system."
  (interactive "noffset? ")
  ;(message (format "\ngoto-offset: %d" offset)
  (let ((bol)                           ;beginning of line offset
        (eol)                           ;end of line offset
        (line-len))                     ;current line length
    (goto-char (point-min))
    (while (> offset 0)
      (setq bol (point))
      (save-excursion
        (end-of-line)
        (setq eol (point)))
      (setq line-len (- eol bol))
      ;(message (format "a. offset: %d point: %d line-len: %d" offset (point) line-len))
      (if (<= offset line-len)          ;if on target line
          (progn
            ;(message "c. target line.")
            (forward-char offset)
            (setq offset 0))
        (progn                          ;else account for this line
          ;(message "d. not target")
          (setq offset (- offset line-len)) ;debug for line-len
          (if (not (equal buffer-file-coding-system 'undecided-unix))
              (setq offset (- offset 1))) ;debit for \r in eclipse buffer
          (setq offset (- offset 1))      ;debit for this line
          (forward-line 1))))
    ;(message (format "e. point: %d" (point)))
    ))
