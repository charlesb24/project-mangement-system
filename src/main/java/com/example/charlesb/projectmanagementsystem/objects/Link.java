package com.example.charlesb.projectmanagementsystem.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    private String url;
    private String title;
    private boolean isCurrent;

}
