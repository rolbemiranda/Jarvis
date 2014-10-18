Set objWordApp = CreateObject("Word.Application")
Set objDoc = objWordApp.Documents.open("C:\tmp\temp.docx")
Set objSelection = objWordApp.Selection
objSelection.EndKey 6,0
objWordApp.Visible = True
SnapShot_TN_Window "KASS", "1"

objDoc.save
objDoc.close
objWordApp.quit

Set objWordApp = Nothing
Set objDoc = Nothing

'=========================================================================
'Name: Capture_Window_Image
''Description: This Function is for Window POT
'=========================================================================
Public Function SnapShot_TN_Window(ByVal strTitle, ByVal intHeaderType)
Set objSnagIt = CreateObject("SNAGIT.ImageCapture")

	objSnagIt.Input = 0
	objSnagIt.Output = 4
	'Set Image Caption
	With objSnagIt.Filters.Annotation
		.EnableCaption = True
		.CaptionText = Now()
		.CaptionOptions.Placement = 3
		.CaptionOptions.CaptionStyle = 1
		.CaptionOptions.Font.Height = 15
		.CaptionOptions.Font.PitchFamily = 5
		.CaptionOptions.TextColor =  RGB(0,0,255)
	End With
	
	objSnagIt.EnablePreviewWindow = False
	objSnagIt.InputWindowOptions.SelectionMethod = 1
	objSnagIt.OutputImageFile.LoadImageDefaults 3
	objSnagIt.AutoScrollOptions.AutoScrollMethod= 1
	objSnagIt.Capture()

	
	
	Do Until objSnagIt.IsCaptureDone
	Loop
	
		objSelection.TypeText strTitle
		'objSelection.Style = intHeaderType
		Call SelectHeaderType(intHeaderType)
		objSelection.TypeParagraph
		objSelection.Paste
		objSelection.TypeParagraph
	
	objWordApp.Visible = True
Set objSnagIt = Nothing
End Function


'=========================================================================
'Name: SnapShot_TN
''Description: This Function is for Auto Scrolling POT
'=========================================================================
Private Sub SelectHeaderType(ByVal intHeaderType)

   Select Case intHeaderType

		Case "1"
			objSelection.Style = -2

		Case "2"
			objSelection.Style = -3

		Case "3"
			objSelection.Style = -4

   End Select
End Sub
