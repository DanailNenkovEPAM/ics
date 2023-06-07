import { Component } from '@angular/core';
import { ImageService } from '../../services/image.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  imageUrl: string = '';
  isLoading: boolean = false;
  message: string = '';

  constructor(private imageService: ImageService) { }

  submitImage() {
    if (this.isValidUrl(this.imageUrl)) {
      this.imageService.analyzeImage(this.imageUrl).subscribe((value) =>{
        //redirect logic to 'image-view'
        
      });
      this.message = "Successfully sent URL!";
    }
    else{
      this.message = "URL is not valid!";
    }
  }

  isValidUrl(url: string): boolean {
    let urlPattern = new RegExp('(http|https)://.+');
    return urlPattern.test(url);
  }
}