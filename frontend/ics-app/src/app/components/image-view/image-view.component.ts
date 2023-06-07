import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ImageService } from '../../services/image.service';

@Component({
  selector: 'app-image-view',
  templateUrl: './image-view.component.html',
  styleUrls: ['./image-view.component.css']
})
export class ImageViewComponent implements OnInit {
  image: any = {};
  tags: any[] = [];

  constructor(private route: ActivatedRoute, private imageService: ImageService, private router: Router) { }

  ngOnInit(): void {
    let id = this.route.snapshot.paramMap.get('id');
    // this.imageService.getImageById(id).subscribe(data => {
    //   this.image = data;
    //   this.tags = data.tags;
    // });
  }

  searchTag(tag: string): void {
    this.router.navigate(['/gallery'], { queryParams: { tag: tag } });
  }
}
