import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileSystemComponent } from './file-system.component';

describe('FileSystemComponent', () => {
  let component: FileSystemComponent;
  let fixture: ComponentFixture<FileSystemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileSystemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileSystemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
